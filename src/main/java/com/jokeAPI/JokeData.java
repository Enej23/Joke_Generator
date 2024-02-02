package com.jokeAPI;

import com.google.gson.JsonArray;

public class JokeData {

        private String joke;
        private String type;

        private JsonArray jokes;
        private String setup;
        private String delivery;

        public String getSetup() {
                return setup;
        }

        public void setSetup(String setup) {
                this.setup = setup;
        }

        public String getDelivery() {
                return delivery;
        }

        public void setDelivery(String delivery) {
                this.delivery = delivery;
        }

        public String getJoke() {
                return joke;
        }

        public void setJoke(String joke) {
                this.joke = joke;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public JsonArray getJokes() {
                return jokes;
        }

        public void setJokes(JsonArray jokes) {
                this.jokes = jokes;
        }
}
