# TicTacToeBackend

The API manages both matchmaking and gameplay. It supports real-time gameplay via Socket.IO and there are only two API endpoints.

### Running the Server
- Install the dependencies via `npm i`
- Run the server with `npm start`

### Endpoints

There are two different API endpoints:

```javascript
POST - /play

Arguments:
{
  playerId    // A unique identifier for the user
}

Description: Finds and joins a match. 
Response: Reponds with the ResponseObject listed below on success or null if it fails
Note: The client application should manage the playerId and should be unique for each player
```

```javascript
POST - /submitMove

Arguments:
{
  player,     // 'X' or 'O'
  row,        // The row to place the piece: 0, 1 or 2
  column,     // The column to place the piece: 0, 1 or 2
  matchId     // Unique identifier for the match, for example: 'M1234567890'
}

Description: Places an 'X' or 'O' at a position 
Response: true on success, false on failure
Note: Attempt can fail for reasons such as: not your turn, position filled, etc.
```

### Socket.IO

You can view the [Socket.IO GitHub repo](https://github.com/socketio/socket.io) for documentation on how to use it with different clients. It supports JS, Java, Swift and more.

The API server emits two different socket events: `'join'` and `'submitMove'`. The `'join'` event is fired after someone has joined a match and the `'submitMove'` after every move is made. These events are fired for all matches and all users.

Here is a JS example for connecting to the TicTacToeBackend API via Socket.IO:

```javascript
const socket = io('http://127.0.0.1:3000', { transports: ['websocket'], upgrade: false });

socket.on('join', (data) => {
  console.log(data);
});

socket.on('submitMove', (data) => {
  console.log(data);
});
```

### Response Object

Both socket events will include a dictionary response with the format:
```javascript
{
  player,       // The player making the action, either 'X' or 'O'
  players,      // Dictionary with the playerId for 'X' and 'O' 
                // for example: { X: 'playerId1', 'O': 'playerId2' }
  matchId,      // Unique identifier for the match, for example: 'M1234567890'
  gameOver,     // Either true or false
  winner,       // Either 'X', 'O' or null
  turn,         // Either 'X' or 'O'
  board         // The layout of the board: 
                // [['', '', ''], ['', '', ''], ['', '', '']]
                // where board[0][0] corresponds to the top left corner
}
```

The `join` socket response will also include the field:
```javascript
isNewUser       // Whether the user joining is playing for the first time
```