import type {
  MicroserviceQueryType,
  MicroserviceType,
  OrchestratorQueryType,
  OrchestratorType,
} from '../types'

export const fethchOrchestratorQuery = async ({
  query,
  repetitions = 1,
  services,
}: OrchestratorQueryType): Promise<OrchestratorType> => {
  const queryParam = new URLSearchParams({
    repetitions: repetitions.toString(),
    ...(services && { services }),
  }).toString()
  const response = await fetch(
    `http://localhost:8100/orchestrator/${query}?${queryParam}`,
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
  return data
}

type QueryDescriptionType = {
  [key: string]: string
}

export const fetchQueryEndpoints = async (): Promise<QueryDescriptionType> => {
  const response = await fetch('http://localhost:8100/getQueryDescriptions', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  if (!response.ok) {
    throw new Error('Network response was not ok')
  }

  const data = await response.json()
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
  return data
}
