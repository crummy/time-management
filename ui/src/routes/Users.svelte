<script>
  import { onMount } from "svelte";
  import { getUsers } from "../api";
  import Menu from "../components/Menu.svelte"

  export let params;
  let { user } = params;
  let users = [];

  onMount(async () => {
    users = await getUsers();
  });
</script>

<Menu permission={user.permission} selected="users"/>

<table>
  <thead>
    <tr>
      <td>User ID</td>
      <td>Name</td>
      <td>Preferred Hours Per Day</td>
      <td>Role</td>
    </tr>
  </thead>
  {#each users as user}
    <tr>
      <td>{user.id}</td>
      <td>{user.name}</td>
      <td>{user.preferredWorkingHoursPerDay}</td>
      <td>{user.permission}</td>
    </tr>
  {/each}
</table>
