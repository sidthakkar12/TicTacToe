module.exports = (io) => {
  io.on('connection', (socket) => {
    console.log(`A socket connected: ${socket.id}`);

    socket.on('disconnect', () => {
      console.log('A socket disconnected');
    });
  });
};
