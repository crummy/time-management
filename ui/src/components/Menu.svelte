<script>
  import page from "page"

  export let selected = undefined
  export let permission
  let admin = permission === "ADMIN"
  let manager = permission === "MANAGER"
</script>

<style>
  ul {
    text-align: left;
    margin: 0;
    padding: 0;
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
</style>

<nav class="container">
  <ul>
    <li class:active={selected === "timesheets"}><a href="#/" on:click={() => page.redirect("/timesheets")}>Timesheets</a></li>
    <li class:active={selected === "profile"}><a href="#/" on:click={() => page.redirect("/profile")}>Profile</a></li>
    {#if manager}
      <li class:active={selected === "users"}><a href="#/" on:click={() => page.redirect("/users")}>Users</a></li>
    {/if}
  </ul>
</nav>