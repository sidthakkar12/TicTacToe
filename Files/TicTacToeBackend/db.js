const matches = {};
const users = {};

function getMatch(matchId) {
  return new Promise((resolve) => {
    resolve(matches[matchId]);
  });
}

function getMatches() {
  return new Promise((resolve) => {
    const matchArray = Object.values(matches);
    resolve(matchArray);
  });
}

function updateMatch(matchId, match) {
  return new Promise((resolve) => {
    matches[matchId] = match;
    resolve();
  });
}

function updateBoard(matchId, board) {
  return new Promise((resolve) => {
    matches[matchId].board = board;
    resolve();
  });
}

function userCreated(playerId) {
  users[playerId] = true;
}

function hasUserBeenCreated(playerId) {
  const created = users[playerId] === true;
  userCreated(playerId);
  return created;
}

module.exports = {
  getMatch,
  getMatches,
  updateMatch,
  updateBoard,
  hasUserBeenCreated,
};
