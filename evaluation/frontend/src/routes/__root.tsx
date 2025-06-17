import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
import {
  createRootRouteWithContext,
  Link,
  Outlet,
} from '@tanstack/react-router'
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools'
import { QueryClient } from '@tanstack/react-query'
import { ConfigProvider, Layout, theme } from 'antd'
import { Content, Header } from 'antd/es/layout/layout'
import '@ant-design/v5-patch-for-react-19'

export const Route = createRootRouteWithContext<{
  queryClient: QueryClient
}>()({
  component: RootComponent,
  notFoundComponent: () => {
    return (
      <div>
        <Link to="/">Start Over</Link>
      </div>
    )
  },
})

function RootComponent() {
  return (
    <ConfigProvider
      theme={{
        algorithm: [theme.defaultAlgorithm],
        cssVar: true,
        hashed: false,
      }}
    >
      <Layout style={{ minHeight: '100vh', width: '100vw' }}>
        <Header
          style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
          <Link to="/" activeOptions={{ exact: true }}>
            Home
          </Link>
        </Header>
        <Content>
          <Outlet />

          <ReactQueryDevtools buttonPosition="top-right" />
          <TanStackRouterDevtools position="bottom-right" />
        </Content>
      </Layout>
    </ConfigProvider>
  )
}
