import { useQuery } from '@tanstack/react-query'
import { fethchOrchestratorQuery } from '../service/queries'
import { OrchestratorResultTable } from './OrchestratorResultTable'
import styles from './OrchestratorPage.module.scss'
import { Result, Spin, Typography } from 'antd'
import { Button } from 'antd'
import { RedoOutlined } from '@ant-design/icons'

export const OrchestratorPage: React.FC<{
  queryEndpoint: string
  microservices: string[]
  repetitions?: number
}> = ({ queryEndpoint, microservices, repetitions }) => {
  const query = useQuery({
    queryKey: ['orchestrator', microservices, queryEndpoint, repetitions],
    queryFn: () =>
      fethchOrchestratorQuery({
        query: queryEndpoint,
        services: microservices.join(','),
        repetitions,
      }),
    enabled: !!queryEndpoint && microservices.length > 0,
    gcTime: 0,
  })

  return (
    <div className={styles.app}>
      <div
        style={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          gap: '16px',
        }}
      >
        <Typography.Title>Orchestrator Query Results</Typography.Title>
        <Button
          className={styles.refreshButton}
          onClick={() => query.refetch()}
          icon={<RedoOutlined />}
          type="text"
        />
      </div>
      {(query.isLoading || query.isFetching) && <Spin size="large" />}
      {query.isError && !query.isFetching && (
        <Result
          status="error"
          title="Query Error"
          subTitle={query.error.message}
        ></Result>
      )}
      {query.isSuccess && !query.isFetching && (
        <OrchestratorResultTable
          data={query.data}
          microservices={microservices}
        />
      )}
    </div>
  )
}
