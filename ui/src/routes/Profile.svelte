<script>
  import Menu from "../components/Menu.svelte";
  import Message, {info, error} from "../components/Message.svelte"
  import { updateUser } from "../api";

  export let params;
  let { user } = params;
  let { preferredHours } = user;

  const handleHoursChange = async () => {
    const updatedUser = {
      ...user,
      preferredWorkingHoursPerDay: preferredHours
    };
    const response = await updateUser(updatedUser)
    if (response.ok) info("Your preference has been updated.")
    else error("Failed to update preferences", await response.json())
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

<Message />
