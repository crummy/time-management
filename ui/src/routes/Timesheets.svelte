<script>
  import Menu from '../components/Menu.svelte'

  export let params;
  let { user } = params;
</script>

<Menu permission={user.permission} selected="timesheets"/>