<script>
  import { onMount } from "svelte";
  import Menu from "../components/Menu.svelte";
  import Message from "../components/Message.svelte";
  import { user } from "../user";

  let message = {};
  let preferredHours;

  onMount(async () => {
    preferredHours = await user.prefer
  })

  const handleHoursChange = async () => {
    const updatedUser = {
      ...(await $user),
      preferredWorkingHoursPerDay: preferredHours
    };
    try {
      user.set(updatedUser);
      message = { text: "Your preference has been updated." };
    } catch (e) {
      console.log(e)
      message = { text: "Failed to update preferences", warning: true };
    }
  };
</script>

<style>
  .highlight {
    font-weight: bold;
  }
</style>

<Menu permission={user.permission} selected="profile" />

{#await $user}
  <h2>Hello...</h2>
{:then user}
  <h2>Hello, {user.name}</h2>

  <p>
    Your current preference is to work
    <span class="highlight">{user.preferredWorkingHoursPerDay}</span>
    hours per day.
  </p>

  <form class="pure-form" on:submit|preventDefault={handleHoursChange}>
    <fieldset>
      <legend>Looking to adjust your hours?</legend>

      <input type="number" bind:value={preferredHours} />

      <button type="submit" class="pure-button pure-button-primary">
        Update
      </button>
    </fieldset>
  </form>

  <Message {message} />
{/await}
