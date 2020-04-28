<script>
  import { onMount } from "svelte";
  import Menu from "../components/Menu.svelte";
  import {
    getTimesheets,
    getAllTimesheets,
    saveTimesheet,
    getUsers,
    updateTimesheet,
    deleteTimesheet
  } from "../api";
  import { user } from "../user";

  let viewAllTimesheets = false;
  let timesheets = [];
  let canViewAllTimesheets = false;
  let today = new Date();
  let editedId;
  let description, hours;
  let day = today.getDate();
  let month = today.getMonth() + 1;
  let year = today.getFullYear();
  let users = {};

  onMount(async () => {
    const user = await $user;
    canViewAllTimesheets =
      user.permission === "ADMIN" || user.permission === "MANAGER";
    timesheets = await getTimesheets(user.id);
  });

  const handleViewAllToggle = async () => {
    if (viewAllTimesheets) {
      const allUsers = await getUsers();
      users = Object.fromEntries(allUsers.map(user => [user.id, user]));
      timesheets = await getAllTimesheets();
    } else {
      const user = await $user;
      timesheets = await getTimesheets(user.id);
    }
  };

  const isOverworked = (date, userId) => {
    const user = users[userId];
    if (!user) {
      return; // wait for users array to fill
    }
    const preferredHours = users[userId].preferredWorkingHoursPerDay;
    const workedHours = timesheets
      .filter(timesheet => timesheet.userId == userId && timesheet.date == date)
      .map(timesheet => timesheet.hours)
      .reduce((a, b) => a + b, 0);

    return workedHours < preferredHours;
  };

  const handleSave = async () => {
    const user = await $user;
    const timesheet = {
      description,
      date: { year, month, day},
      hours
    };
    await saveTimesheet(user.id, timesheet);
    handleViewAllToggle();
    description = null;
  };

  const handleEditSave = async (timesheet) => {
    const user = await $user;
    await updateTimesheet(user.id, timesheet)
    editedId = null;
  }

  const handleDelete = async (timesheet) => {
    await deleteTimesheet(timesheet.userId, timesheet.id)
    handleViewAllToggle();
    editedId = null;
  }
</script>

<style>
  #viewAll {
    margin: 1em;
  }

  table {
    width: 100%;
    margin-top: 1em;
  }

  .year {
    width: 5em;
  }

  .day,
  .month,
  .hoursWorked {
    width: 3em;
  }

  .description {
    width: 100%;
  }

  .overworked {
    background-color: coral;
  }
</style>

<Menu selected="timesheets" />

{#if canViewAllTimesheets}
  <label for="viewAll">
    <input
      id="viewAll"
      type="checkbox"
      on:click={() => {
        viewAllTimesheets = !viewAllTimesheets;
        handleViewAllToggle();
      }} />
    View All
  </label>
{/if}

<table class="pure-table">
  <thead>
    <tr>
      {#if viewAllTimesheets}
        <th>User ID</th>
      {/if}
      <th>Description</th>
      <th>Date</th>
      <th>Hours</th>
      <th />
    </tr>
  </thead>
  {#each timesheets as timesheet}
    {#if timesheet.id === editedId}
      <tr>
        {#if viewAllTimesheets}<td>{timesheet.userId}</td>{/if}
        <td>
          <input
            class="description"
            placeholder="Description"
            bind:value={timesheet.description} />
        </td>
        <td>
          <input
            class="year"
            type="number"
            placeholder="Year"
            bind:value={timesheet.date.year} />
          /
          <input
            class="month"
            type="number"
            placeholder="Month"
            bind:value={timesheet.date.month} />
          /
          <input
            class="day"
            type="number"
            placeholder="Day"
            bind:value={timesheet.date.day} />
        </td>
        <td>
          <input
            class="hoursWorked"
            type="number"
            placeholder="8"
            bind:value={timesheet.hours} />
        </td>
        <td>
          <a href="#/" on:click={() => handleEditSave(timesheet)}>Save</a> / <a href="#/" on:click={() => handleDelete(timesheet)}>Delete</a>
        </td>
      </tr>
    {:else}
      <tr
        class:overworked={viewAllTimesheets && isOverworked(timesheet.date, timesheet.userId)}>
        {#if viewAllTimesheets}
          <td>{timesheet.userId}</td>
        {/if}
        <td>{timesheet.description}</td>
        <td>{timesheet.date.year}/{timesheet.date.month}/{timesheet.date.day}</td>
        <td>{timesheet.hours}</td>
        <td>
          <a href="#/" on:click={() => (editedId = timesheet.id)}>Edit</a>
        </td>
      </tr>
    {/if}
  {/each}
  {#if !viewAllTimesheets}
    <tr>
      <td>
        <input
          class="description"
          type="description"
          placeholder="Description"
          bind:value={description} />
      </td>
      <td>
        <input
          class="year"
          type="number"
          placeholder="Year"
          bind:value={year} />
        /
        <input
          class="month"
          type="number"
          placeholder="Month"
          bind:value={month} />
        /
        <input class="day" type="number" placeholder="Day" bind:value={day} />
      </td>
      <td>
        <input
          class="hoursWorked"
          type="number"
          placeholder="8"
          bind:value={hours} />
      </td>

      <td>
        <a href="#/" on:click={handleSave}>Save</a>
      </td>
    </tr>
  {/if}
</table>
