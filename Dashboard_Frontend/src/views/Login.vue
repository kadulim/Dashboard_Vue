<template>
  <div class="login-page">

    <div class="login-card">

      <h1>Login</h1>

      <p class="subtitle">
        Entre na sua conta
      </p>

      <form @submit.prevent="login">

        <div class="input-group">
          <label for="email">E-mail</label>

          <input
            id="email"
            v-model="email"
            type="email"
            placeholder="seu@email.com"
            required
          />
        </div>

        <div class="input-group">
          <label for="password">Senha</label>

          <input
            id="password"
            v-model="password"
            type="password"
            placeholder="••••••••"
            required
          />
        </div>

        <p v-if="error" class="error">
          {{ error }}
        </p>

        <button type="submit" :disabled="loading">
          {{ loading ? "Entrando..." : "Entrar" }}
        </button>

      </form>

    </div>

  </div>
</template>

<script setup>
import { ref } from "vue";

const email = ref("");
const password = ref("");

const loading = ref(false);
const error = ref("");

async function login() {

  error.value = "";
  loading.value = true;

  try {

    const response = await fetch(
      "http://localhost:8080/api/auth/login",
      {
        method: "POST",

        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify({
          email: email.value,
          password: password.value
        })
      }
    );

    if (!response.ok) {
      throw new Error("E-mail ou senha inválidos");
    }

    const user = await response.json();

    console.log("Login realizado:", user);

    alert("Login realizado com sucesso!");

  } catch (err) {

    error.value = err.message;

  } finally {

    loading.value = false;

  }
}
</script>

<style scoped>

.login-page {
  min-height: 100vh;

  display: flex;
  align-items: center;
  justify-content: center;

  background: #f4f4f5;
}

.login-card {
  width: 100%;
  max-width: 380px;

  padding: 35px;

  background: white;

  border-radius: 12px;

  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}

h1 {
  margin: 0;

  font-size: 28px;
}

.subtitle {
  margin-top: 8px;
  margin-bottom: 30px;

  color: #777;
}

.input-group {
  margin-bottom: 20px;
}

label {
  display: block;

  margin-bottom: 7px;

  font-size: 14px;
  font-weight: 600;
}

input {
  width: 100%;
  box-sizing: border-box;

  padding: 12px;

  border: 1px solid #ddd;
  border-radius: 7px;

  outline: none;

  font-size: 14px;
}

input:focus {
  border-color: #42b883;
}

button {
  width: 100%;

  padding: 12px;

  border: none;
  border-radius: 7px;

  background: #42b883;
  color: white;

  font-size: 15px;
  font-weight: 600;

  cursor: pointer;
}

button:hover {
  background: #369b6f;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error {
  margin-bottom: 15px;

  color: #dc2626;

  font-size: 14px;
}

</style>