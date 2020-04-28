<script>
  import { onMount } from "svelte";
  import { getUsers, deleteUser, updateUser } from "../api";
  import Menu from "../components/Menu.svelte";

  let users = [];
  let editedId;

  onMount(async () => {
    users = await getUsers();
  });

  const del = async user => {
    await deleteUser();
    users = await getUsers();
  };

  const save = async user => {
    await updateUser(user);
    users = await getUsers();
    editedId = null;
  };
</script>

<style>
  table {
    width: 100%;
    margin-top: 1em;
  }
</style>

<Menu selected="users" />

<table class="pure-table">
  <thead>
    <tr>
      <th>User ID</th>
      <th>Name</th>
      <th>Preferred Hours Per Day</th>
      <th>Role</th>
      <th />
    </tr>
  </thead>
  {#each users as user}
    {#if user.id == editedId}
      <tr>
        <td>{user.id}</td>
        <td>
          <input class="name" bind:value={user.name} />
        </td>
        <td>
          <input class="userId" bind:value={user.preferredWorkingHoursPerDay} />
        </td>
        <td>
          <select id="permission" class="pure-input" bind:value={user.permission}>
            <option>ADMIN</option>
            <option>MANAGER</option>
            <option>USER</option>
          </select>
        </td>
        <td>
          <a href="#/" on:click={() => save(user)}>Save</a>
        </td>
      </tr>
    {:else}
      <tr>
        <td>{user.id}</td>
        <td>{user.name}</td>
        <td>{user.preferredWorkingHoursPerDay}</td>
        <td>{user.permission}</td>
        <td>
          <a href="#/" on:click={() => (editedId = user.id)}>Edit</a>
          /
          <a href="#/" on:click={() => del(user)}>Delete</a>
        </td>
      </tr>
    {/if}
  {/each}
</table>
