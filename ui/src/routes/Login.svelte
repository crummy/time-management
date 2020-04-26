<script>
  import { signIn, signUp } from '../api'
  import Message, { error } from '../components/Message.svelte'
  import page from 'page'

  let logIn = true;
  let username, password, name

  const handleSignIn = async (event) => {
    const response = await signIn(username, password)
    const json = await response.json()
    if (!response.ok) {
      const reason = json.error ? json.error : "An unknown error occurred";
      error("Sign in failed", reason)
    } else {
      page.redirect('/')
    }
  }

  const handleSignUp = async (event) => {
    const response = await signUp(username, password, name)
    const json = await response.json()
    if (!response.ok) {
      const reason = json.error ? json.error : "An unknown error occurred";
      error("Sign up failed", reason)
    } else {
      console.log("Signed up!")
      page.redirect('/')
    }
  }
</script>

<style>
  form {
    text-align: left;
    display: inline;
  }
</style>

<h1>Login</h1>

{#if logIn}
  <Message />
  <form class="pure-form pure-form-aligned" on:submit|preventDefault={handleSignIn}>
    <fieldset>
      <legend>Existing user</legend>

      <div class="pure-control-group">
        <label for="name">Username</label>
        <input id="name" type="text" placeholder="Username" bind:value={username} required/>
        <span class="pure-form-message-inline">This is a required field.</span>
      </div>

      <div class="pure-control-group">
        <label for="password">Password</label>
        <input id="password" type="password" placeholder="Password" bind:value={password} required/>
      </div>

      <button type="submit" class="pure-button pure-button-primary">
        Sign In
      </button>
    </fieldset>
  </form>

  <a href="javascript:;" on:click={() => logIn = false}>Create a new account</a>
{:else}
  <Message />
  <form class="pure-form pure-form-aligned" on:submit|preventDefault={handleSignUp}>
    <fieldset>
      <legend>Create a new account</legend>

      <div class="pure-control-group">
        <label for="username">Username</label>
        <input id="username" type="text" placeholder="Username" bind:value={username} required/>
        <span class="pure-form-message-inline">This is a required field.</span>
      </div>

      <div class="pure-control-group">
        <label for="name">Name</label>
        <input id="name" type="text" placeholder="Name" bind:value={name} required/>
      </div>

      <div class="pure-control-group">
        <label for="password">Password</label>
        <input id="password" type="password" placeholder="Password" bind:value={password} required/>
      </div>

      <button type="submit" class="pure-button pure-button-primary">
        Sign In
      </button>
    </fieldset>
  </form>
  <a href="javascript:;" on:click={() => logIn = true}>Sign in to an existing account</a>
{/if}
