<script>
  import Users from "./routes/Users.svelte";
  import Login from "./routes/Login.svelte";
  import Error from "./routes/Error.svelte";
  import Timesheet from "./routes/Timesheet.svelte";
  import router from "page";
  import { check as isLoggedIn } from "./api"

  let page;
  let params;
  
  const checkLogin = async (ctx, next) => {
    const loggedIn = await isLoggedIn()
    if (!loggedIn) router.redirect("/login")
    else next()
  }

  router("/", checkLogin, () => router.redirect("/users"));
  router("/login", () => (page = Login))
  router("/users", checkLogin, () => (page = Users));
  router("/*", () => (page = Error))

  router.start();
</script>

<style>
  main {
    text-align: center;
    padding: 1em;
    max-width: 240px;
    margin: 0 auto;
  }

  :global(h1) {
    color: #ff3e00;
    text-transform: uppercase;
    font-size: 4em;
    font-weight: 100;
  }

  @media (min-width: 640px) {
    main {
      max-width: none;
    }
  }
</style>

<svelte:head>
	<title>Time Management</title>
</svelte:head>
<main>
  <svelte:component this={page} {params} />
</main>

<link
  rel="stylesheet"
  href="https://unpkg.com/purecss@1.0.1/build/pure-min.css"
  integrity="sha384-oAOxQR6DkCoMliIh8yFnu25d7Eq/PHS21PClpwjOTeU2jRSq11vu66rf90/cZr47"
  crossorigin="anonymous" />
