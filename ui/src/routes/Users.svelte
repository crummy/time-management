<script>
  import { onMount } from "svelte";
  import { getUsers } from "../api";
  import Menu from "../components/Menu.svelte"

  let users = [];

  onMount(async () => {
    users = await getUsers();
  });
</script>

<style>
  table {
    width: 100%;
    margin-top: 1em;
  }
</style>

<Menu selected="users"/>

<table class="pure-table">
  <thead>
    <tr>
      <th>User ID</th>
      <th>Name</th>
      <th>Preferred Hours Per Day</th>
      <th>Role</th>
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
