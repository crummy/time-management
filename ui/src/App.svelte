<script>
  import Users from "./routes/Users.svelte";
  import Login from "./routes/Login.svelte";
  import Error from "./routes/Error.svelte";
  import Profile from "./routes/Profile.svelte";
  import Timesheets from "./routes/Timesheets.svelte";
  import router from "page";
  import { getUser } from "./user"

  let page;
  export let params = {}
  
  const checkLogin = async (ctx, next) => {
    params.user = await getUser()
    if (params.user) {
      next()
    } else {
      router.redirect("/login")
    }
  }

  router("/", checkLogin, () => router.redirect("/timesheets"));
  router("/login", () => (page = Login))
  router("/users", checkLogin, () => (page = Users));
  router("/timesheets", checkLogin, () => (page = Timesheets));
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
  <svelte:component this={page} {params} />
</main>

<link
  rel="stylesheet"
  href="https://unpkg.com/purecss@1.0.1/build/pure-min.css"
  integrity="sha384-oAOxQR6DkCoMliIh8yFnu25d7Eq/PHS21PClpwjOTeU2jRSq11vu66rf90/cZr47"
  crossorigin="anonymous" />
