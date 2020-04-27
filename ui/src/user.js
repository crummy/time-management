import { check, updateUser as updateUserApi} from './api'

let user

export const getUser = async () => {
  if (user) return user
  user = await check()
  console.log(user)
  return user
}

export const updateUser = async (user) => {
  const response = await updateUserApi(user)
  if (response.ok) {
    user = response.json()
    return user
  } else {
    throw response.json()
  }
}