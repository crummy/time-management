<script>
  export let message = {}
</script>

{#if message && message.text}
  <div id="message" class:warning={message.warning}>
    {#if message.title}
      <div id="title">{message.title}</div>
    {/if}
    {message.text}
  </div>
{/if}

<style>
  #title {
    font-weight: bold
  }

  #message {
    background-color: lightblue;
    padding: 1em;
    margin: 1em;
  }

  .warning {
    background-color: orangered !important;
  }
</style>