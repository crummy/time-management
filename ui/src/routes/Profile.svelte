<script>
  import { onMount } from "svelte";
  import Menu from "../components/Menu.svelte";
  import Message from "../components/Message.svelte";
  import { updateUser, getUser } from "../user";

  export let params;
  let { user } = params;
  let { preferredHours } = user;
  let message = {};

  onMount(async () => {
    user = await getUser();
  });

  const handleHoursChange = async () => {
    const updatedUser = {
      ...user,
      preferredWorkingHoursPerDay: preferredHours
    };
    try {
      const user = await updateUser(updatedUser);
      message = { text: "Your preference has been updated." };
    } catch (e) {
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
