const baseUrl = process.env.BASE_API_URL;

const jsonHeaders = {
  Accept: "application/json",
  "Content-Type": "application/json"
}

export const getUsers = async () => {
  let response = await fetch(`${baseUrl}/users`);
  return response.json();
};

export const createUser = (user) => {
  return fetch(`${baseUrl}/users`, {
    method: "POST",
    body: JSON.stringify(user),
    headers: jsonHeaders
  });
}

export const signIn = (id, password) => {
  return fetch(`${baseUrl}/login`, {
    method: "POST",
    body: JSON.stringify({ id, password }),
    headers: jsonHeaders
  })
}

export const signUp = (id, password, name) => {
  return fetch(`${baseUrl}/users`, {
    method: "POST",
    body: JSON.stringify({ id, password, name }),
    headers: jsonHeaders
  })
}