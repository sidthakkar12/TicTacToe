module.exports = {
  extends: 'airbnb-base',
  rules: {
    'no-console': 'off',
    'max-len': 'off',
    'no-await-in-loop': 'off',
    'import/no-extraneous-dependencies': ['error', { devDependencies: true }],
    'no-async-promise-executor': 'off',
  },
  env: {
    jest: true,
  },
};
