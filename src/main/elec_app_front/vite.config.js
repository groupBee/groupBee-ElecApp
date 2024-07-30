import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // 프록시 설정
      '/elecapp': {
        target: 'http://localhost:9522',
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/elecapp/, '')
      }
    }
  }
})
