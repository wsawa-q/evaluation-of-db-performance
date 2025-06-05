export type OrchestratorQueryType = {
  query: string
  services?: string
}

export type MicroserviceQueryType = OrchestratorQueryType & {
  microservice: string
}

export type OrchestratorType = {
  query: string
  description: string
  cayenne?: MetricType
  ebean?: MetricType
  jooq?: MetricType
  jdbc?: MetricType
  myBatis?: MetricType
  springDataJpa?: MetricType
}

export type OrchestratorQueryResponseType = {
  data: OrchestratorType
}

export type MicroserviceType = {
  elapsed: number
  delta: number
  status: string
}

export type MicroserviceQueryResponseType = {
  data: MicroserviceType
}

export type MetricType = {
  status: string
  repetition: number
  averageExecutionTime: string
  averageMemoryUsage: string
}

export type OrchestratorSearchParamsType = {
  items: string[]
  query: string
}
