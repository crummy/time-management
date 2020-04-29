<script>
  import { onMount } from "svelte";
  import { getSummary } from "../api";

  let summary = []

  onMount(async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const from =
      urlParams.get("from") !== "undefined" ? urlParams.get("from") : undefined;
    const to =
      urlParams.get("to") !== "undefined" ? urlParams.get("to") : undefined;
    const user = urlParams.get("user");
    const summaries = await getSummary(from, to, user);
    summary = Object.entries(summaries)
  });
</script>

<style>
  table {
    width: 100%;
  }
</style>

<table class="pure-table">
  <thead>
    <tr>
      <th>Date</th>
      <th>Total Hours</th>
      <th>Note</th>
    </tr>
  </thead>
  {#each summary as [date, day]}
    <tr>
      <td>{date}</td>
      <td>{day.totalHours}</td>
      <td>{day.descriptions.join(", ")}</td>
    </tr>
  {/each}
</table>
