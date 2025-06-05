import { useQuery } from '@tanstack/react-query'
import { fetchQueryEndpoints } from '../service/queries'

export const useQueryEndpoints = () => {
  return useQuery({
    queryKey: ['queryEndpoints'],
    queryFn: fetchQueryEndpoints,
    refetchOnWindowFocus: false,
  })
}
