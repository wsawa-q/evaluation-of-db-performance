import { useQuery } from '@tanstack/react-query'
import { fethchOrchestratorQuery } from '../service/queries'
import { OrchestratorResultTable } from './OrchestratorResultTable'
import styles from './OrchestratorPage.module.scss'
import { Result, Spin, Typography } from 'antd'

export const OrchestratorPage: React.FC<{
  queryEndpoint: string
  microservices: string[]
}> = ({ queryEndpoint, microservices }) => {
  const query = useQuery({
    queryKey: ['orchestrator', microservices, queryEndpoint],
    queryFn: () =>
      fethchOrchestratorQuery({
        query: queryEndpoint,
        services: microservices.join(','),
      }),
    enabled: !!queryEndpoint && microservices.length > 0,
    gcTime: 0,
  })

  return (
    <div className={styles.app}>
      <Typography.Title>Orchestrator Query Results</Typography.Title>
      {query.isLoading && <Spin size="large" />}
      {query.isError && (
        <Result
          status="error"
          title="Query Error"
          subTitle={query.error.message}
        ></Result>
      )}
      {query.isSuccess && (
        <OrchestratorResultTable
          data={query.data}
          microservices={microservices}
        />
      )}
    </div>
  )
}
