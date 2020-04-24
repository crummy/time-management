<script context="module">
  let title, message;

  export const error = (m, t) => {
    message = message
    title = title
    document.querySelector("message").classList.add("warning")
  }

  export const info = (m, t) => {
    message = m
    title = t
    document.querySelector("message").classList.remove("warning")
  }
</script>

{#if message}
  <div id="message" class="warning">
    {#if title}
      <div id="title">{title}</div>
    {/if}
    {message}
  </div>
{/if}

<style>
  .warning {
    background-color: lightcoral;
  }

  #message {
    background-color: lightblue;
  }
</style>