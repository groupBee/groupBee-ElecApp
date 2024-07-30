// vite.config.js
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/elecapp': {
        target: 'http://localhost:9522', // 백엔드 서버의 실제 URL
        changeOrigin: true,
        secure: false,
        rewrite: path => path.replace(/^\/elecapp/, '')
      }
    }
  }
});