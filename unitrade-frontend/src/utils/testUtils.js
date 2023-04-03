import { getLoginStatus, LOGIN, LOGOUT } from './client';

// If the user is not logged in, log them in and log them out after the function is done
// Needed because some tests require the user to not be logged in, but the API requires the user to be logged in
let accessBackend = async (defaultUser, func) => {
  let loggedIn = getLoginStatus();
  if (!loggedIn) {
    await LOGIN(defaultUser.email, defaultUser.password);
  }

  await func();

  if (!loggedIn) {
    await LOGOUT();
  }
};

export default accessBackend;
