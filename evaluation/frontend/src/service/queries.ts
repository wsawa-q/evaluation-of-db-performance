import type {
  MicroserviceQueryType,
  MicroserviceType,
  OrchestratorQueryType,
  OrchestratorType,
} from '../types'

export const fethchOrchestratorQuery = async ({
  query,
  services,
}: OrchestratorQueryType): Promise<OrchestratorType> => {
  const response = await fetch(
    `http://localhost:8100/orchestrator/${query}?${services ? `services=${services}` : ''}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    },
  )

  if (!response.ok) {
    throw new Error('Network response was not ok')
  }

  const data = await response.json()
  console.log('Orchestrator Query Data:', data)
  return data
}

export const fetchMicroserviceQuery = async ({
  microservice,
  query,
}: MicroserviceQueryType): Promise<MicroserviceType> => {
  const response = await fetch(
    `http://localhost:8100/${microservice}/${query}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    },
  )

  if (!response.ok) {
    throw new Error('Network response was not ok')
  }

  const data = await response.json()
  console.log('Microservice Query Data:', data)
  return data
}

export const fetchQueryEndpoints = async (): Promise<string[]> => {
  const response = await fetch('http://localhost:8100/getQueryEndpoints', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  if (!response.ok) {
    throw new Error('Network response was not ok')
  }

  const data = await response.json()
  console.log('Query Endpoints:', data)
  return data
}

export const fetchMicroserviceEndpoints = async (): Promise<string[]> => {
  const response = await fetch(
    'http://localhost:8100/getMicroserviceEndpoints',
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    },
  )

  if (!response.ok) {
    throw new Error('Network response was not ok')
  }

  const data = await response.json()
  console.log('Microservice Endpoints:', data)
  return data
}
