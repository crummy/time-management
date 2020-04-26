<script>
  import Menu from '../components/Menu.svelte'
  import Message, { error, info } from '../components/Message.svelte'

  export let params;
  let { user: { permission = null } = {} } = params;

  error("An error was encountered trying to load the page you requested.")
</script>

<Menu {permission} />

<Message />