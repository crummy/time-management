const baseUrl = process.env.BASE_API_URL;

const jsonHeaders = {
  Accept: "application/json",
  "Content-Type": "application/json"
}

export const check = async () => {
  let response = await fetch(`${baseUrl}/check`);
  if (response.ok) return response.json();
  else return null;
}

export const signOut = () => {
  return fetch(`${baseUrl}/signout`);
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

export const updateUser = (user) => {
  return fetch(`${baseUrl}/users/${user.id}`, {
    method: "PATCH",
    body: JSON.stringify(user),
    headers: jsonHeaders
  });
}

export const deleteUser = (id) => {
  return fetch(`${baseUrl}/users/${id}`, {
    method: "DELETE"
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
  return fetch(`${baseUrl}/signup`, {
    method: "POST",
    body: JSON.stringify({ id, password, name }),
    headers: jsonHeaders
  })
}

export const getTimesheets = async (userId) => {
  let response = await fetch(`${baseUrl}/users/${userId}/timesheets`);
  return response.json();
}

export const getAllTimesheets = async () => {
  let response = await fetch(`${baseUrl}/timesheets`);
  return response.json();
}

export const saveTimesheet = async (userId, timesheet) => {
  return fetch(`${baseUrl}/users/${userId}/timesheets`, {
    method: "POST",
    body: JSON.stringify(timesheet),
    headers: jsonHeaders
  })
}

export const updateTimesheet = async (userId, timesheet) => {
  return fetch(`${baseUrl}/users/${userId}/timesheets/${timesheet.id}`, {
    method: "PATCH",
    body: JSON.stringify(timesheet),
    headers: jsonHeaders
  })
}

export const deleteTimesheet = async (userId, timesheetId) => {
  return fetch(`${baseUrl}/users/${userId}/timesheets/${timesheetId}`, {
    method: "DELETE"
  })
}