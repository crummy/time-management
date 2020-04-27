<script>
  import Menu from '../components/Menu.svelte'
  import Message from '../components/Message.svelte'

  export let params;
  let { user: { permission = null } = {} } = params;

</script>

<Menu {permission} />

<Message params={{title: "Error", text: "An error was encountered trying to load the page you requested."}}/>