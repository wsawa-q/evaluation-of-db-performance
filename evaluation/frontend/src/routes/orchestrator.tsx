import { createFileRoute } from '@tanstack/react-router'
import { OrchestratorPage } from '../components/OrchestratorPage'
import { useSearch } from '@tanstack/react-router'
import type { OrchestratorSearchParamsType } from '../types'

export const Route = createFileRoute('/orchestrator')({
  validateSearch: (
    search: Record<string, unknown>,
  ): OrchestratorSearchParamsType => {
    const items = (search.items as string[]) || []
    const query = (search.query as string) || ''
    return {
      items,
      query,
    }
  },
  component: RouteComponent,
})

function RouteComponent() {
  const search = useSearch({
    from: Route.fullPath,
  })

  return (
    <OrchestratorPage
      queryEndpoint={search.query}
      microservices={search.items}
    />
  )
}
