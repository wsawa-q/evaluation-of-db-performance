import { Carousel, Table } from 'antd'
import type { OrchestratorType } from '../types'
import { Column } from '@ant-design/charts'
import styles from './OrchestratorResultTable.module.scss'
import Title from 'antd/es/typography/Title'

export const OrchestratorResultTable: React.FC<{
  data: OrchestratorType
  microservices?: string[]
}> = ({ data, microservices }) => {
  const columns = [
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Repetitions', dataIndex: 'repetition', key: 'repetition' },
    {
      title: 'Average Time Execution',
      dataIndex: 'averageExecutionTime',
      key: 'averageExecutionTime',
    },
    {
      title: 'Average Memory Usage',
      dataIndex: 'averageMemoryUsage',
      key: 'averageMemoryUsage',
    },
  ]

  console.log({ data })

  const ormNames: Record<string, string> = {
    cayenne: 'Cayenne',
    ebean: 'Ebean',
    jooq: 'JOOQ',
    jdbc: 'JDBC',
    myBatis: 'MyBatis',
    springDataJpa: 'Spring Data JPA',
  }

  const rows = Object.entries(data)
    .filter(
      ([key, value]) =>
        typeof value === 'object' &&
        value !== null &&
        microservices?.includes(key),
    )
    .map(([key, value]) => {
      if (typeof value !== 'object') {
        return null
      }

      return {
        name: ormNames[key] || key,
        repetition: value.repetition,
        averageExecutionTime: Number(value.averageExecutionTime),
        averageMemoryUsage: value.averageMemoryUsage,
      }
    })

  console.log('Rows:', rows)

  const chartData = Object.entries(rows).map(([key, value]) => ({
    name: value?.name || key,
    averageExecutionTime: value?.averageExecutionTime,
    averageMemoryUsage: value?.averageMemoryUsage,
  }))

  console.log('Chart Data:', chartData)

  const chartConfig = {
    width: 600,
    height: 400,
    lazyLoad: true,
    centerMode: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    legend: {
      position: 'top-left',
    },
  }

  const timeChartConfig = {
    data: chartData,
    xField: 'name',
    yField: 'averageExecutionTime',
    ...chartConfig,
  }

  const memoryChartConfig = {
    data: chartData,
    xField: 'name',
    yField: 'averageMemoryUsage',
    ...chartConfig,
  }

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'space-between',
        flexDirection: 'column',
        alignItems: 'center',
      }}
    >
      <Table
        dataSource={rows}
        columns={columns}
        rowKey="name"
        pagination={false}
        bordered
      />
      <div>
        <Carousel
          style={{
            width: '100%',
            maxWidth: 1200,
          }}
          arrows
          draggable
          // autoplay
          // autoplaySpeed={5000}
        >
          <div>
            <div className={styles.slide}>
              <Title level={3} className={styles.chartTitle}>
                Average Execution Time
              </Title>
              <Column {...timeChartConfig} />
            </div>
          </div>
          <div>
            <div className={styles.slide}>
              <Title level={3} className={styles.chartTitle}>
                Average Memory Usage
              </Title>
              <Column {...memoryChartConfig} />
            </div>
          </div>
        </Carousel>
      </div>
    </div>
  )
}
