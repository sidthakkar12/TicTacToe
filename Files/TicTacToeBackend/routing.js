const bodyParser = require('body-parser');
const pjson = require('./package.json');
const game = require('./game.js');

const limit = '300KB'; // File size limit for API requests

module.exports = (app, io) => {
  // CORS (Cross Origin Request) configuration allows browsers to call the API endpoints
  app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    next();
  });

  app.set('io', io);

  app.use(bodyParser.urlencoded({ extended: true, limit }));
  app.use((req, res, next) => {
    bodyParser.json({ limit })(req, res, next);
  });

  // Test that api is up
  app.get('/', (req, res) => {
    res.status(200).send('TicTacToe API is Live');
  });

  // Gets the API version
  app.get('/version', (req, res) => {
    res.status(200).send(pjson.version);
  });

  app.post('/version', (req, res) => {
    res.status(200).send(pjson.version);
  });

  app.post('/play', (req, res) => {
    const {
      playerId,
    } = req.body;

    let missingArg = null;
    if (playerId == null) {
      missingArg = 'playerId';
    }

    if (missingArg != null) {
      const error = `Missing argument for '${missingArg}'`;
      const errorMessage = JSON.stringify({ error });
      console.log(`/play: ${error}`);
      res.status(403).send(errorMessage);
    } else {
      game.play(io, playerId)
        .then((result) => {
          const resultMessage = JSON.stringify({ result });
          res.status(200).send(resultMessage);
        });
    }
  });

  app.post('/submitMove', (req, res) => {
    const {
      player,
      matchId,
      row,
      column,
    } = req.body;

    let missingArg = null;
    if (player == null) {
      missingArg = 'player';
    } else if (matchId == null) {
      missingArg = 'matchId';
    } else if (row == null) {
      missingArg = 'row';
    } else if (column == null) {
      missingArg = 'column';
    }

    if (missingArg != null) {
      const error = `Missing argument for '${missingArg}'`;
      const errorMessage = JSON.stringify({ error });
      console.log(`/submitMove: ${error}`);
      res.status(403).send(errorMessage);
    } else {
      game.submitMove(io, player, matchId, row, column)
        .then((result) => {
          const resultMessage = JSON.stringify({ result });
          res.status(200).send(resultMessage);
        });
    }
  });
};
