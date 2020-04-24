<script>
  import Users from "./routes/Users.svelte";
  import Home from "./routes/Home.svelte";
  import Login from "./routes/Login.svelte";
  import router from "page";
  import { check } from "./api"

  let page;
	let params;

  router("/", async () => {
		if (await check()) page = Home
		else page = Login
	});
  router("/users", () => (page = Users));

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
