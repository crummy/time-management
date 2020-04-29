<script>
  import Users from "./routes/Users.svelte";
  import Login from "./routes/Login.svelte";
  import Error from "./routes/Error.svelte";
  import Profile from "./routes/Profile.svelte";
  import Timesheets from "./routes/Timesheets.svelte";
  import Summary from "./routes/Summary.svelte"
  import router from "page";
  import { user } from "./user"

  let page;
  
  const checkLogin = async (ctx, next) => {
    const u = await $user
    if (u) {
      next()
    } else {
      console.log(`User ${u} is not logged in`)
      router.redirect("/login")
    }
  }

  router("/", checkLogin, () => router.redirect("/timesheets"));
  router("/login", () => (page = Login))
  router("/users", checkLogin, () => (page = Users));
  router("/timesheets", checkLogin, () => (page = Timesheets));
  router("/timesheets/summary", checkLogin, () => (page = Summary));
  router("/profile", checkLogin, () => (page = Profile));
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

  h1 {
    color: #ff3e00;
    text-transform: uppercase;
    font-size: 4em;
    font-weight: 100;
    text-align: center;
  }

  @media (min-width: 640px) {
    main {
      max-width: 800px;
    }
  }
</style>

<svelte:options accessors/>
<svelte:head>
	<title>Time Management</title>
</svelte:head>
<h1>Time Management</h1>
<main>
  <svelte:component this={page} />
</main>

<link
  rel="stylesheet"
  href="https://unpkg.com/purecss@1.0.1/build/pure-min.css"
  integrity="sha384-oAOxQR6DkCoMliIh8yFnu25d7Eq/PHS21PClpwjOTeU2jRSq11vu66rf90/cZr47"
  crossorigin="anonymous" />
