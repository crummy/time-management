<script>
  import page from "page"
  import { user } from "../user"
  import { signOut } from "../api"

  export let selected = undefined

  const logOut = async () => {
    await signOut()
    user.reset()
    page.redirect("/")
  }
</script>

<style>
  ul {
    text-align: left;
    margin: 0;
    padding: 0;
    margin-bottom: 1em;
    list-style-type: none;
  }

  nav {
    background-color: lightcoral;
    padding-left: 1em;
  }

  li {
    font-size: 1.5em;
    padding: 1em;
    border: 1px solid coral;
    margin: 0;
    display: inline-block;
  }

  .active {
    background-color: coral;
    font-weight: bold;
  }

  .right {
    float: right;
  }
</style>

<nav class="container">
  <ul>
    <li class:active={selected === "timesheets"}><a href="#/" on:click={() => page.redirect("/timesheets")}>Timesheets</a></li>
    <li class:active={selected === "profile"}><a href="#/" on:click={() => page.redirect("/profile")}>Profile</a></li>
    {#await $user}
      <!-- TODO: How to get rid of this block?-->
    {:then user}
      {#if user.permission === "manager" || user.permission === "ADMIN"}
        <li class:active={selected === "users"}><a href="#/" on:click={() => page.redirect("/users")}>Users</a></li>
      {/if}
      <li class="right"><a href="#/" on:click={logOut}>Log Out</a></li>
    {/await}
  </ul>
</nav>