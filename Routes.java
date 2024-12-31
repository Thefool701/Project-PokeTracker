public class Routes {
    private String routeName;
    private String caughtPokemon;
    private String pokemonTyping;
    private boolean routeStatus;

    public Routes(
            String routeName,
            String caughtPokemon,
            String pokemonTyping,
            boolean routeStatus) {
        this.routeName = routeName;
        this.caughtPokemon = caughtPokemon;
        this.pokemonTyping = pokemonTyping;
        this.routeStatus = routeStatus;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getCaughtPokemon() {
        return caughtPokemon;
    }

    public void setCaughtPokemon(String caughtPokemon) {
        this.caughtPokemon = caughtPokemon;
    }

    public String getPokemonTyping() {
        return pokemonTyping;
    }

    public void setPokemonTyping(String pokemonTyping) {
        this.pokemonTyping = pokemonTyping;
    }

    public boolean isRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(boolean routeStatus) {
        this.routeStatus = routeStatus;
    }
}
