const db = require('./db.js');

function turn(board) {
  let x = 0;
  let o = 0;

  board.forEach((row) => {
    row.forEach((spot) => {
      if (spot === 'X') {
        x += 1;
      } else if (spot === 'O') {
        o += 1;
      }
    });
  });

  if (x === o) {
    return 'X';
  }
  return 'O';
}

function checkRow(row) {
  let piece = null;
  let isSet = true;
  row.forEach((spot) => {
    if (piece == null && spot !== '') {
      piece = spot;
    } else if (spot !== piece) {
      isSet = false;
    }
  });
  return { isSet, piece };
}

function isOver(board) {
  const top = board[0];
  const topSet = checkRow(top);

  if (topSet.isSet) {
    return { gameOver: true, winner: topSet.piece };
  }

  const middle = board[1];
  const middleSet = checkRow(middle);

  if (middleSet.isSet) {
    return { gameOver: true, winner: middleSet.piece };
  }

  const bottom = board[2];
  const bottomSet = checkRow(bottom);

  if (bottomSet.isSet) {
    return { gameOver: true, winner: bottomSet.piece };
  }

  const left = [board[0][0], board[1][0], board[2][0]];
  const leftSet = checkRow(left);

  if (leftSet.isSet) {
    return { gameOver: true, winner: leftSet.piece };
  }

  const center = [board[0][1], board[1][1], board[2][1]];
  const centerSet = checkRow(center);

  if (centerSet.isSet) {
    return { gameOver: true, winner: centerSet.piece };
  }

  const right = [board[0][2], board[1][2], board[2][2]];
  const rightSet = checkRow(right);

  if (rightSet.isSet) {
    return { gameOver: true, winner: rightSet.piece };
  }

  const leftDiag = [board[0][0], board[1][1], board[2][2]];
  const leftDiagSet = checkRow(leftDiag);

  if (leftDiagSet.isSet) {
    return { gameOver: true, winner: leftDiagSet.piece };
  }

  const rightDiag = [board[2][0], board[1][1], board[0][2]];
  const rightDiagSet = checkRow(rightDiag);

  if (rightDiagSet.isSet) {
    return { gameOver: true, winner: rightDiagSet.piece };
  }

  let filled = 0;
  board.forEach((row) => {
    row.forEach((spot) => {
      if (spot === 'X') {
        filled += 1;
      } else if (spot === 'O') {
        filled += 1;
      }
    });
  });

  return { gameOver: filled === 9 };
}

function newGame(playerId) {
  const matchId = `M${(new Date()).getTime()}`;
  const players = {};
  players.X = playerId;

  return {
    matchId,
    players,
    board: [['', '', ''], ['', '', ''], ['', '', '']],
  };
}

function findMatch(playerId) {
  return new Promise((resolve) => {
    db.getMatches()
      .then((matchesArray) => {
        const matchCount = matchesArray.length;
        for (let i = 0; i < matchCount; i += 1) {
          const match = matchesArray[i];
          const {
            players,
            matchId,
            board,
          } = match;

          const playerIds = Object.values(players);
          const {
            gameOver,
          } = isOver(board);

          if (!gameOver && !(playerIds.includes(playerId))) {
            if (playerIds.length === 0) {
              players.X = playerId;
              match.players = players;
              db.updateMatch(matchId, match)
                .then(() => {
                  resolve(match);
                });
              return;
            }

            if (playerIds.length === 1) {
              players.O = playerId;
              match.players = players;
              db.updateMatch(matchId, match)
                .then(() => {
                  resolve(match);
                });
              return;
            }
          }
        }

        const match = newGame(playerId);
        db.updateMatch(match.matchId, match)
          .then(() => {
            resolve(match);
          });
      });
  });
}

function play(io, playerId) {
  return new Promise((resolve) => {
    const isNewUser = !db.hasUserBeenCreated(playerId);
    findMatch(playerId)
      .then((match) => {
        if (match == null) {
          resolve(null);
        } else {
          const {
            matchId,
            board,
            players,
          } = match;

          const {
            gameOver,
            winner,
          } = isOver(board);
          const t = turn(board);

          let player;
          const pieces = Object.keys(players);
          const playersCount = pieces.length;
          for (let i = 0; i < playersCount; i += 1) {
            const piece = pieces[i];
            const pid = players[piece];
            if (pid === playerId) {
              player = piece;
            }
          }

          const message = {
            player,
            players,
            matchId,
            gameOver,
            winner,
            turn: t,
            board,
            isNewUser,
          };
          io.emit('join', message);
          resolve(message);
        }
      });
  });
}

function addPiece(matchId, board, row, column, piece) {
  return new Promise((resolve) => {
    const t = turn(board);

    if (t !== piece) {
      resolve({
        added: false,
        gameOver: false,
        turn: t,
        board,
      });
      return;
    }

    if (row < 0 || row > 3) {
      resolve({
        added: false,
        gameOver: false,
        turn: t,
        board,
      });
      return;
    }

    if (column < 0 || column > 3) {
      resolve({
        added: false,
        gameOver: false,
        turn: t,
        board,
      });
      return;
    }

    if (piece !== 'X' && piece !== 'O') {
      resolve({
        added: false,
        gameOver: false,
        turn: t,
        board,
      });
      return;
    }

    if (board[row][column] !== '') {
      resolve({
        added: false,
        gameOver: false,
        turn: t,
        board,
      });
    } else {
      const b = board;
      b[row][column] = piece;

      db.updateBoard(matchId, b)
        .then(() => {
          const {
            gameOver,
            winner,
          } = isOver(b);

          resolve({
            added: true,
            gameOver,
            winner,
            turn: t,
            board: b,
          });
        });
    }
  });
}

function submitMove(io, player, matchId, row, column) {
  return new Promise((resolve) => {
    db.getMatch(matchId)
      .then((match) => {
        const {
          board,
          players,
        } = match;

        let {
          gameOver,
        } = isOver(board);
        if (gameOver) {
          resolve(false);
          return;
        }

        addPiece(matchId, board, row, column, player)
          .then((result) => {
            const {
              added,
            } = result;
            const b = result.board;

            if (added) {
              const res = isOver(b);
              const { winner } = res;
              gameOver = res.gameOver;

              const t = turn(b);

              const message = {
                player,
                players,
                matchId,
                gameOver,
                winner,
                turn: t,
                board: b,
              };
              io.emit('submitMove', message);
            }
            resolve(added);
          });
      });
  });
}

module.exports = {
  play,
  submitMove,
};
