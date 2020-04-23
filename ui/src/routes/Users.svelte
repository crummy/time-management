<script>
  import { onMount } from 'svelte'
  import { getUsers } from '../api'

  let loadingUsers = true
  let users = []

  onMount(async () => {
    users = await getUsers()
    loadingUsers = false
  })
</script>

<h1>Users</h1>

<table>
  {#each users as user}
    <tr>
      <td>user.id</td>
      <td>user.name</td>
      <td>user.permission</td>
    </tr>
  {/each}
</table>