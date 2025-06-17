import type { MetricType, OrchestratorType, ServiceMetricType } from '../types'

export function exportOrchestratorCsv(
  orchestrator: OrchestratorType,
  fileName: string = 'metrics.csv',
): void {
  // Helper to wrap CSV cells with quotes when needed
  const quote = (value: string | number): string => {
    const str = String(value)
    return /[",\n]/.test(str) ? `"${str.replace(/"/g, '""')}"` : str
  }

  // Gather implementation keys (exclude metadata fields)
  const implKeys = Object.keys(orchestrator).filter(
    (key) => key !== 'query' && key !== 'description',
  ) as (keyof OrchestratorType)[]

  // Define CSV header
  const headers = [
    'query',
    'description',
    'implementation',
    'iteration',
    'elapsed',
    'result',
    'delta',
    'status',
    'gcCount',
    'heapUsedAvg',
    'allocatedInsideTLAB',
    'allocatedOutsideTLAB',
    'totalAllocated',
  ]

  // Build rows
  const rows: string[][] = []
  implKeys.forEach((implKey) => {
    const metricGroup = orchestrator[implKey] as MetricType | undefined
    if (!metricGroup) return

    metricGroup.iterationResults.forEach(
      (serviceMetric: ServiceMetricType, idx: number) => {
        const { elapsed, result, delta, status, jfr } = serviceMetric
        rows.push([
          quote(orchestrator.query),
          quote(orchestrator.description),
          quote(implKey),
          String(idx + 1),
          String(elapsed),
          String(result),
          String(delta),
          quote(status),
          String(jfr.gcCount),
          String(jfr.heapUsedAvg),
          String(jfr.allocatedInsideTLAB),
          String(jfr.allocatedOutsideTLAB),
          String(jfr.totalAllocated),
        ])
      },
    )
  })

  // Compose CSV content
  const csvContent = [
    headers.map(quote).join(','),
    ...rows.map((r) => r.join(',')),
  ].join('\n')

  // Create blob and trigger download
  const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' })
  const link = document.createElement('a')
  if (link.download !== undefined) {
    const url = URL.createObjectURL(blob)
    link.setAttribute('href', url)
    link.setAttribute('download', fileName)
    link.style.visibility = 'hidden'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }
}
