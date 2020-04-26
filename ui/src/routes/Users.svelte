<script>
  import { onMount } from "svelte";
  import { getUsers } from "../api";
  import Menu from "../components/Menu.svelte"

  let loadingUsers = true;
  let users = [];

  onMount(async () => {
    users = await getUsers();
    loadingUsers = false;
  });
</script>

<Menu selected="users"/>

<table>
  <thead>
    <tr>
      <td>User ID</td>
      <td>Name</td>
      <td>Role</td>
    </tr>
  </thead>
  {#each users as user}
    <tr>
      <td>{user.id}</td>
      <td>{user.name}</td>
      <td>{user.permission}</td>
    </tr>
  {/each}
</table>
