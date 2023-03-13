import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    target: 'esnext',
    ssr: false,
  },
  test: {
    globals: true,
    environment: "happy-dom"
  },
});
