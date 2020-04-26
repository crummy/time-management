<script context="module">
  let title, message;
  let warning = false

  export const error = (m, t) => {
    message = m
    title = t
    warning = true
    console.log(message)
  }

  export const info = (m, t) => {
    message = m
    title = t
    warning = false
  }
</script>

{#if message}
  <div id="message" class:warning={warning}>
    {#if title}
      <div id="title">{title}</div>
    {/if}
    {message}
  </div>
{/if}

<style>
  #message {
    background-color: lightblue;
    padding: 1em;
    margin: 1em;
  }

  .warning {
    background-color: orangered !important;
  }
</style>