const BASE_URL = 'http://localhost:8080/';

function headersConstructor(customHeaders = {}) {
  let headers = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
    'Access-Control-Allow-Headers': 'Content-Type',
  }

  // Merge custom headers with default headers
  if (customHeaders) {
    headers = { ...headers, ...customHeaders };
  }

  return headers;
}

/**
 * GET request to the API
 * @param {string} path - The path of the API. E.g. "api/users"
 * @param {object} customHeaders - Custom object of headers to be added to the request
 * @returns Promise<any>
 */
async function GET(path, customHeaders = {}) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'GET',
      headers: headersConstructor(customHeaders),
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
 * @param {object} customHeaders - Custom object of headers to be added to the request
 * @returns Promise<any>
 */
async function POST(path, body, customHeaders = {}) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'POST',
      headers: headersConstructor(customHeaders),
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
 * @param {object} customHeaders - Custom object of headers to be added to the request
 * @returns Promise<any>
 */
async function PUT(path, body = {}, customHeaders = {}) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'PUT',
      headers: headersConstructor(customHeaders),
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
 * @param {object} customHeaders - Custom object of headers to be added to the request
 * @returns Promise<any>
 */
async function DELETE(path, customHeaders = {}) {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'DELETE',
      headers: headersConstructor(customHeaders)
    });
    return response.json();
  } catch (e) {
    console.log(e);
    return { error: e };
  }
}

async function LOGIN(email, password) {

  // Clear the cookies
  document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

  userKey = btoa(`${email}:${password}`);
  return POST("login", {
    'Authorization': `Basic ${userKey}`,
    'Access-Control-Allow-Headers': 'Content-Type, Authorization',
    'Access-Control-Allow-Credentials': 'true'
  });
}

async function LOGOUT() {
  return GET("logout");
}

export { GET, POST, PUT, DELETE, LOGIN, LOGOUT };
