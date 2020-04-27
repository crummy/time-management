<script>
  export let message = {}
  let { title = undefined, text = undefined, warning = false } = message
</script>

{#if message && message.text}
  <div id="message" class:warning={warning}>
    {#if title}
      <div id="title">{title}</div>
    {/if}
    {text}
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