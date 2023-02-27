const BASE_URL = 'http://localhost:8080/';

const get = async (path) => {
  let response = await fetch(`${BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
      'Access-Control-Allow-Headers': 'Content-Type',
    },
  });
  return response.json();
};

const post = async (path, body) => {
  try {
    let response = await fetch(`${BASE_URL}${path}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        'Access-Control-Allow-Headers': 'Content-Type',
      },
      body: JSON.stringify(body),
    });
    return response.json();
  } catch (e) {
    console.log(e);
  }
};

export { get, post };
