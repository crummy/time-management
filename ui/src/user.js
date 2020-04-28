import { writable } from 'svelte/store'
import { check, updateUser } from './api'

const createUser = () => {
  const { subscribe, set } = writable(check())
  return {
    subscribe,
    reset: () => set(Promise.resolve(null)),
    set: (user) => set(Promise.resolve(user)),
    update: (user) => set(updateUser(user).then(r => r.json()))
  }
}

export const user = createUser()