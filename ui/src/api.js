const baseUrl = process.env.BASE_API_URL;

export const getUsers = async () => {
  let response = await fetch(`${baseUrl}/users`);
  return response.json();
};

export const createUser = (user) => {
  return fetch(`${baseUrl}/users`, {
    method: "POST",
    body: JSON.stringify(user),
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  });
}