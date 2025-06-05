import { createFileRoute, useNavigate } from '@tanstack/react-router'
import {
  Button,
  Checkbox,
  Form,
  Input,
  Select,
  Typography,
  type CheckboxOptionType,
} from 'antd'
import { useQueryEndpoints } from '../hooks/useQueryEndpoints'

export const Route = createFileRoute('/')({
  component: Home,
})

const { Title } = Typography

const options: CheckboxOptionType<string>[] = [
  {
    label: 'MyBatis',
    value: 'myBatis',
  },
  {
    label: 'JOOQ',
    value: 'jooq',
  },
  {
    label: 'JDBC',
    value: 'jdbc',
  },
  {
    label: 'Ebean',
    value: 'ebean',
  },
  {
    label: 'Cayenne',
    value: 'cayenne',
  },
  {
    label: 'Spring Data JPA',
    value: 'springDataJpa',
  },
]

function Home() {
  const [form] = Form.useForm()
  const { data: queryEndpoints } = useQueryEndpoints()
  const navigate = useNavigate()

  const allValues = options.map((opt) => opt.value)

  const onFinish = (values: { items: string[]; query: string }) => {
    navigate({
      to: '/orchestrator',
      search: {
        items: values.items,
        query: values.query,
      },
    })
  }

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        flexDirection: 'column',
        alignItems: 'center',
        padding: '16px',
        maxWidth: '600px',
        margin: '0 auto',
      }}
    >
      <Title level={1}>ORM Benchmark</Title>
      <Form
        form={form}
        name="orm-benchmark"
        onFinish={onFinish}
        layout="vertical"
      >
        <Form.Item name="items" initialValue={[]}>
          <Checkbox.Group options={options} />
        </Form.Item>
        <Form.Item
          noStyle
          shouldUpdate={(prevValues, currentValues) =>
            prevValues.items !== currentValues.items
          }
        >
          {({ getFieldValue }) => {
            const selectedList: string[] = getFieldValue('items') || []
            const checkedCount = selectedList.length
            const isAllChecked = checkedCount === allValues.length
            const isIndeterminate =
              checkedCount > 0 && checkedCount < allValues.length

            return (
              <Form.Item>
                <Checkbox
                  indeterminate={isIndeterminate}
                  checked={isAllChecked}
                  onChange={(e) => {
                    const nextList = e.target.checked ? allValues : []
                    form.setFieldsValue({ items: nextList })
                  }}
                >
                  Check all
                </Checkbox>
              </Form.Item>
            )
          }}
        </Form.Item>

        <div style={{ display: 'flex', gap: '8px' }}>
          <Form.Item
            name="query"
            label="Select Query"
            rules={[{ required: true, message: 'Please select a query!' }]}
            style={{ flexGrow: 1 }}
          >
            <Select
              placeholder="Select a query"
              options={queryEndpoints?.map((endpoint) => ({
                label: endpoint,
                value: endpoint,
              }))}
              style={{ width: '100%' }}
              allowClear
              showSearch
              filterOption={(input, option) =>
                (option?.label ?? '')
                  .toLowerCase()
                  .includes(input.toLowerCase())
              }
              optionFilterProp="label"
            />
          </Form.Item>
          <Form.Item
            name="repetitions"
            label="Repetitions"
            initialValue={1}
            rules={[{ required: true, message: 'Please input repetitions!' }]}
          >
            <Input type="number" min={1} />
          </Form.Item>
        </div>

        <Form.Item>
          <Button type="primary" htmlType="submit">
            Execute
          </Button>
        </Form.Item>
      </Form>
    </div>
  )
}
