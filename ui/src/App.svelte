<script>
	import Users from './routes/Users.svelte'
	import Home from './routes/Home.svelte'
  import router from "page"

	let page
	let params

	router('/', () => page = Home)
	router('/users', () => page = Users)

	router.start()
</script>

<main>
 <svelte:component this={page} params={params} />
</main>

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
