const BASE_URL = 'http://localhost:8080/';

function headersConstructor(includeSession = true) {
  let headers = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
    'Access-Control-Allow-Headers': 'Content-Type',
  }

  if (includeSession) {
    headers['Authorization'] = `Basic ${getUserKey()}`;
    headers['Access-Control-Allow-Headers'] = 'Content-Type, Authorization';
    headers["Access-Control-Allow-Credentials"] = "true";
  }

  return headers;
}

function getUserKey() {
  const expTime = localStorage.getItem('expTime');
  if (!expTime || expTime < Date.now()) {
    localStorage.removeItem('userKey'); // Clear the expired key
    throw new Error('Session expired');
  }
  const userKey = localStorage.getItem('userKey');
  if (!userKey) throw new Error('Session expired');
  return userKey;
}

/**
 * Set or update the user key in the local storage
 * @param {string} email - The email of the user in plain text
 * @param {string} password - The password of the user in plain text
 * @param {number} durationInDays - The duration of the session in days. Default is 1 day
 */
function setUserKey(email, password, durationInDays = 1) {
  // Construct the user key
  const userKey = btoa(`${email}:${password}`);
  const expTime = Date.now() + 1000 * 60 * 60 * 24 * durationInDays;
  localStorage.setItem('userKey', userKey);
  localStorage.setItem('expTime', expTime);
}

/**
 * GET request to the API
 * @param {string} path - The path of the API. E.g. "api/users"
 * @param {boolean} includeSession - Whether to include the session key in the request. Default is true
 * @returns Promise<any>
 */
async function GET(path, includeSession = true) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'GET',
      headers: headersConstructor(includeSession),
    });
    return response.json();

  } catch (e) {
    console.log(e);
    return { error: e };
  }
}

/**
 * POST request to the API
 * @param {string} path - The path of the API. E.g. "api/users"
 * @param {boolean} includeSession - Whether to include the session key in the request. Default is true
 * @returns Promise<any>
 */
async function POST(path, body, includeSession = true) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'POST',
      headers: headersConstructor(includeSession),
      body: JSON.stringify(body),
    });
    return response.json();
  } catch (e) {
    console.log(e);
    return { error: e };
  }
}

/**
 * PUT request to the API
 * @param {string} path - The path of the API. E.g. "api/users"
 * @param {boolean} includeSession - Whether to include the session key in the request. Default is true
 * @returns Promise<any>
 */
async function PUT(path, body = {}, includeSession = true) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'PUT',
      headers: headersConstructor(includeSession),
      body: JSON.stringify(body),
    });
    return response.json();
  } catch (e) {
    console.log(e);
    return { error: e };
  }
}

/**
 * DELETE request to the API
 * @param {string} path - The path of the API. E.g. "api/users"
 * @param {boolean} includeSession - Whether to include the session key in the request. Default is true
 * @returns Promise<any>
 */
async function DELETE(path, includeSession = true) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'DELETE',
      headers: headersConstructor(includeSession)
    });
    return response.json();
  } catch (e) {
    console.log(e);
    return { error: e };
  }
}

export { GET, POST, PUT, DELETE, setUserKey };
